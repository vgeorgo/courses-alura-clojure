(ns hospital.class4
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn person-arrives-swap! [hospital, person]
  (swap! hospital h.logic/person-arrives :waiting person))

(defn simulate-day-1 []
  (let [hospital (atom (h.model/new-hospital))
        people ["111" "222" "333" "444" "555" "666"]
        start-arriving-thread #(.start (Thread. (fn [] (person-arrives-swap! hospital %))))]

    ; Simulating using mapv to force map to have all items initialized.
    ; Instead of its default lazy behavior.
    (mapv start-arriving-thread people)
    (.start (Thread. (fn [] (Thread/sleep 2000) (pprint hospital))))))

(simulate-day-1)



; Defines two different prototypes so the simulate can use both.
(defn start-arriving-thread-two-signatures
  ([hospital]
   (fn [person] (start-arriving-thread-two-signatures hospital person)))
  ([hospital person]
   (.start (Thread. (fn [] (person-arrives-swap! hospital person))))))

(defn simulate-day-2 []
  (let [hospital (atom (h.model/new-hospital))
        people ["111" "222" "333" "444" "555" "666"]
        arriving-mapper (start-arriving-thread-two-signatures hospital)]

    ; Simulating using mapv to force map to have all items initialized.
    ; Instead of its default lazy behavior.
    (mapv arriving-mapper people)
    (.start (Thread. (fn [] (Thread/sleep 2000) (pprint hospital))))))

(simulate-day-2)



(defn start-arriving-thread [hospital person]
  (.start (Thread. (fn [] (person-arrives-swap! hospital person)))))

(defn simulate-day-3 []
  (let [hospital (atom (h.model/new-hospital))
        people ["111" "222" "333" "444" "555" "666"]
        ; Partial gives the same effect of the last "start-arriving-thread-two-signatures"
        ; using only one parameter
        arriving-mapper (partial start-arriving-thread hospital)]

    (mapv arriving-mapper people)
    (.start (Thread. (fn [] (Thread/sleep 2000) (pprint hospital))))))

(simulate-day-3)



(defn simulate-day-4
  "Simulating using mapv to force map to have all items initialized.
  Instead of its default lazy behavior."
  []
  (let [hospital (atom (h.model/new-hospital))
        people ["111" "222" "333" "444" "555" "666"]]

    ; Equivalent to 'for', and nested for as well (not used in this example)
    (doseq [person people]
      (start-arriving-thread hospital person))
    ; If the loop has no interest in the items, do times is the way to go
    ;(dotimes [n 6]
    ;  (start-arriving-thread hospital n)))

    (.start (Thread. (fn [] (Thread/sleep 2000) (pprint hospital))))))

(simulate-day-4)