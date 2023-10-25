(ns gm-io.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :refer [includes? starts-with?]]
            [clojure-mail.core :refer [inbox search-inbox connected?]]
            [clojure-mail.parser :refer [html->text]]
            [clojure-mail.gmail :as gmail]
            [clojure-mail.message :as message]
            [clojure.string :as string]
            [gm-io.creds :refer [fetch-account]])
  (:gen-class))

(defn find-in-inbox
  "Basic convenience fn for searching an inbox. Note: this is an
   expensive operation on a large inbox."
  [search-term m]
  (let [{:keys [user pass]} m
        msgs (-> (gmail/store user pass)
                 (search-inbox search-term))]
    (doall
     (map message/read-message msgs))))


(defn recent-boa
  "expensive operation, returns a future so that interaction in a repl won't get 
   blocked for a long period of time"
  [m]
  (future
    (->> m
         (find-in-inbox [:received-after :yesterday])
         (filter #(let [addr (-> % :from first :address)]
                    (includes? addr "bankofamerica"))))))

(defn boa-alerts [m]
  (->> m
       (find-in-inbox "Bank Of America")
       (map message/read-message)
       vec))

;; TODO do some nested map destructuring here
(defn extract-body [msg]
  (let [{:keys [body]} msg]
    (condp #(starts-with? %2 %1) (:content-type body)
      "TEXT/HTML" (html->text (:body body))
      "TEXT/PLAIN" (:body body))))

(defn -main [& args] (println "hello world"))

(comment

  (def recent (recent-boa
               (fetch-account
                "http://localhost:8080/v1/config/account/gmail-tstout")))

  (realized? recent)

  (count @recent)

  (type (first @recent))

  (-> @recent first :body)

  (frequencies )


  (partition)

  @recent

  (def t-body
    (extract-body (-> @recent (nth 6))))

  t-body

  (clojure.string/split t-body #"(at:|On:|Amount:)")

  (clojure.string/split t-body #" ")

  
  (frequencies ["at:"])


  (clojure.string/split "abcedef alsalfj adfasfd Amount: $ 884.91" #"Amount:")
  ;;
  )
