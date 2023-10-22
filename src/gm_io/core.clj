(ns gm-io.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :refer [includes? starts-with?]]
            [clojure-mail.core :refer [inbox search-inbox connected?]]
            [clojure-mail.parser :refer [html->text]]
            [clojure-mail.gmail :as gmail]
            [clojure-mail.message :as message]
            [clojure.string :as string])
  (:gen-class))

;; (defn fetch-inbox [m]
;;   (let [{:keys [user pass]} m]
;;     (->> (gmail/store user pass)
;;          inbox)))

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
  "expensive operation, returns a future"
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

(defn extract-body [msg]
  (condp #(starts-with? %2 %1) msg
    "TEXT/HTML" (do 1)
    "TEXT/PLAIN" (do 2)))

(defn -main [& args] (println "hello world"))

(comment

  (identity 4)

  (def recent (recent-boa {:user "todd.tstout@gmail.com"
                           :pass "foobar"}))

  (realized? recent)

  (count @recent)

  (type (first @recent))


  @recent

  (extract-body "TEXT/HTML;")


  (map #(-> % :body :content-type) @recent)

  (html->text (-> (second @recent) :body :body))

  (clojure.string/split "abcedef alsalfj adfasfd Amount: $ 884.91" #"Amount:")
  ;;
  )
