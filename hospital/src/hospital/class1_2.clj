(ns hospital.class1-2
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

; Not thread safe. Defining a global variable may cause unexpected results
; and it is not a good practice
(defn simulate-day-1 []
  (def hospital (h.model/new-hospital))
  (def hospital (h.logic/person-arrives hospital :waiting 111))
  (def hospital (h.logic/person-arrives hospital :waiting 222))
  (def hospital (h.logic/person-arrives hospital :waiting 333))
  (def hospital (h.logic/person-arrives hospital :lab1 444))
  (def hospital (h.logic/person-arrives hospital :lab2 555))

  (def hospital (h.logic/call-person hospital :lab2))
  (def hospital (h.logic/call-person hospital :waiting))

  (def hospital (h.logic/person-arrives hospital :waiting 666))
  (def hospital (h.logic/person-arrives hospital :waiting 777))
  (def hospital (h.logic/person-arrives hospital :waiting 888))
  ; This will give error because it will reach max size of the queue
  (def hospital (h.logic/person-arrives hospital :waiting 999))
  (pprint hospital))

; (simulate-day-1)


(defn person-arrives-bad [person]
  (def hospital (h.logic/person-arrives-with-sleep hospital :waiting person))
  (println "After insert" person))

(defn simulate-day-with-sleep
  []
  ; Simulating concurrency problem with threads + sleep time
  ; Running threads we will not run at the same order as they are
  (def hospital (h.model/new-hospital))
  (.start (Thread. (fn [] (person-arrives-bad "111"))))
  (.start (Thread. (fn [] (person-arrives-bad "222"))))
  (.start (Thread. (fn [] (person-arrives-bad "333"))))
  (.start (Thread. (fn [] (person-arrives-bad "444"))))
  (.start (Thread. (fn [] (person-arrives-bad "555"))))
  (.start (Thread. (fn [] (person-arrives-bad "666"))))
  (.start (Thread. (fn [] (Thread/sleep 4000)
                     (pprint hospital)))))


(simulate-day-with-sleep)