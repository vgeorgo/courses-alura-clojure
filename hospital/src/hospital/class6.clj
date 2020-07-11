(ns hospital.class6
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn person-arrives-ref-set!
  "Update the queue with set-ref, need to run inside transaction"
  [hospital, person]
  (let [queue (get hospital :waiting)]
    (ref-set queue (h.logic/person-arrives-queue @queue person))))

(defn person-arrives-alter!
  "Update the queue with alter, need to run inside transaction"
  [hospital, person]
  (let [queue (get hospital :waiting)]
    (alter queue h.logic/person-arrives-queue person)))

(defn async-person-arrives! [hospital, person]
  (future
    (Thread/sleep (rand 5000))
    (dosync
      (println "Trying to synchronize person: " person)
      (person-arrives-alter! hospital person))))

(defn simulate-day-1 []
  (let [hospital (h.model/new-hospital-with-ref)]

    (dosync
      (person-arrives-ref-set! hospital "victor")
      (person-arrives-ref-set! hospital "alessandra")
      (person-arrives-alter! hospital "billy")
      (person-arrives-alter! hospital "leo")
      (person-arrives-alter! hospital "fred")
      ;(person-arrives-ref-set! hospital "predo") ; Will throw Queue is full error
      )

    (pprint hospital)))

; (simulate-day-1)

(defn simulate-day-2-async []
  (let [hospital (h.model/new-hospital-with-ref)]

    ; Some of these futures will have the state as ready, others will have exceptions
    ; since the queue max size is 5
    ; This is defined as global just to see the value on console after execution
    ; (just typing (use 'hospital.class6) and futures after)
    (def futures (mapv #(async-person-arrives! hospital %) (range 10)))

    (future
      (Thread/sleep 8000)
      (pprint hospital))))

(simulate-day-2-async)