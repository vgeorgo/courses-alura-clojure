(ns hospital.examples.collections
  (:import (clojure.lang PersistentQueue))
  (:use [clojure pprint]))

; On a vector, elements are inserted and popped from the end
(defn test-vector []
  (let [elements [111 222]]
    (println "Vector: ")
    (println (conj elements 333))
    (println (pop elements))))

(test-vector)

; On a list, elements are inserted and popped from the beginning
(defn test-list []
  (let [elements '(111 222)]
    (println "List: ")
    (println (conj elements 333))
    (println (pop elements))))

(test-list)

; On a set, elements are not ordered, so pop would give an error
; Set would not allow two of the same value inside it
(defn test-set []
  (let [elements #{111 222}]
    (println "Set: ")
    (println (conj elements 333))
    ; (println (pop elements))
    (println (conj elements 111))))

(test-set)

; On a queue, elements are inserted on the on the end, and popped from the beginning
(defn test-queue []
  (let [elements (conj PersistentQueue/EMPTY 111 222)]
    (println "Queue: ")
    (println (seq (conj elements 333)))
    (println (seq (pop elements)))
    (println (peek elements))
    (pprint elements)))

(test-queue)