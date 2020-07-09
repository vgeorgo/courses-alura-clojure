(ns hospital.model
  (:import (clojure.lang PersistentQueue)))

(def empty-queue PersistentQueue/EMPTY)

(defn new-hospital []
  {:waiting empty-queue
   :lab1 empty-queue
   :lab2 empty-queue
   :lab3 empty-queue})