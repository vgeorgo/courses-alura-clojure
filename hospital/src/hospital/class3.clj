(ns hospital.class3
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

; This makes this symbol globally available for any script that uses this namespace
; It could be redefined in another script without any control
; (def name "Victor")


(defn test-atom []
  ; atom - Adds a layer around the map, which gives it the possibility to change values
  (let [hospital (atom { :waiting h.model/empty-queue })]
    (pprint hospital)
    ; Gets the value of an atom
    (pprint (deref hospital))
    ; Same as (deref)
    (pprint @hospital)

    ; swap! - is explicitly saying that it will change the hospital symbol
    ; ! is a syntax that the action will have a collateral effect
    ; This is one of the ways to change an atom
    (swap! hospital assoc :lab1 h.model/empty-queue)
    (pprint @hospital)

    (swap! hospital update :lab1 conj 111)
    (pprint @hospital)))

; (test-atom)


(defn person-arrives-bad! [hospital, person]
  ; swap! keeps the data synchronized and solves the concurrency problem without locking,
  ; making a thread rerun the code if the value was changed by another thread.
  ; We need to keep in mind that the logic inside h.logic/person-arrives-with-sleep
  ; will be repeated, so it should be simple.
  (swap! hospital h.logic/person-arrives-with-sleep :waiting person)
  (println "After insert" person))

(defn simulate-day-with-sleep []
  (let [hospital (atom (h.model/new-hospital))]
    (.start (Thread. (fn [] (person-arrives-bad! hospital "111"))))
    (.start (Thread. (fn [] (person-arrives-bad! hospital "222"))))
    (.start (Thread. (fn [] (person-arrives-bad! hospital "333"))))
    (.start (Thread. (fn [] (person-arrives-bad! hospital "444"))))
    (.start (Thread. (fn [] (person-arrives-bad! hospital "555"))))
    (.start (Thread. (fn [] (person-arrives-bad! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


(simulate-day-with-sleep)