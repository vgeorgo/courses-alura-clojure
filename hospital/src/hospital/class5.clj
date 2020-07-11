(ns hospital.class5
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn person-arrives! [hospital, person]
  (swap! hospital h.logic/person-arrives :waiting person))

(defn transfer! [hospital from to]
  (swap! hospital h.logic/transfer from to))

(defn simulate-day-1 []
  (let [hospital (atom (h.model/new-hospital))]

    (person-arrives! hospital "victor")
    (person-arrives! hospital "alessandra")
    (person-arrives! hospital "billy")
    (person-arrives! hospital "leo")
    (person-arrives! hospital "fred")

    (transfer! hospital :waiting :lab1)
    (transfer! hospital :waiting :lab2)
    (transfer! hospital :waiting :lab2)
    (transfer! hospital :lab1 :lab3)

    (pprint hospital)))

(simulate-day-1)