(ns gm-io.boa-parse
  (:require [devs.core :refer [evolve
                               on
                               do-transition
                               outputs
                               guard
                               new-state]]))


;; (defn parse-boa-state-machine
;;   "todo"
;;   [words]
;;   (-> {:input-alphabet  #{:read :write :empty :close :empty-buffers}
;;        :output-alphabet #{:read :write}
;;        :state-alphabet  #{:reading :writing :draining :closed}
;;        :state           :reading
;;        :words           words}
;;       (on :reading :read :reading)
;;       (on-event :read
;;                 (in-state? :writing)
;;                 (new-state :writing))
;;       (on-event :write
;;                 (in-state? :writing)
;;                 (new-state :writing))
;;       (on-event :write
;;                 (in-state? :draining)
;;                 (guard all-data-drained?
;;                        (generate-event :empty-buffers))
;;                 (new-state :draining))
;;       (on-event :empty-buffers
;;                 (in-state? :writing)
;;                 (new-state :reading))
;;       (on-event :empty-buffers
;;                 (in-state? :draining)
;;                 (new-state :closed))
;;       (on-event :close
;;                 (in-state? :reading)
;;                 (new-state :closed))
;;       (on-event :close
;;                 (in-state? :writing)
;;                 (new-state :draining))
;;       (outputs  :reading  (generate [:read]))
;;       (outputs  :writing  (generate [:read :write]))
;;       (outputs  :draining (generate [:write]))
;;       (outputs  :closed   (generate []))))




(comment
  (reductions + [1 2 3])

  ()

  ;;
  )


