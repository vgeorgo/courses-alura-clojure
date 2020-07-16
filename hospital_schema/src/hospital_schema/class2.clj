(ns hospital-schema.class2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; Forces an error if the schema is not followed
(s/set-fn-validation! true)

; Using record is ok when when can receive extra data,
; if we want to be rigid its not the best way.
(s/defrecord Patient
  [id :- Long, name :- s/Str])

(pprint (Patient. 1 "Victor"))
; Will not throw error
(pprint (Patient. "1" "Victor"))
; Will not throw error
(pprint (Patient. "test" "Victor"))
; Will not throw error
(pprint (map->Patient {:id "test" :name "Victor"}))
; Will throw error
;(pprint (s/validate Patient (map->Patient {:id "teste" :name "Victor"})))
; Will throw error. :id2 does not exist
; (pprint (s/validate Patient (map->Patient {:id2 1 :name "Victor"})))

; Creates a schema to be used later
(def Patient2
  "Schema of a patient"
  {:id s/Num, :name s/Str})

(println "Explain: " (s/explain Patient2))
(println "Validate: " (s/validate Patient2 {:id 1 :name "Victor"}))
; Wrong keys/Typos are detected, extras parameters are no allowed
; (println "Validate: " (s/validate Patient2 {:id 1 :names "Victor"}))

; Enforces the schema on the entry and return of the function
(s/defn new-patient :- Patient2
  [id :- s/Num, name :- s/Str]
  ; This would return an error because of the key :plan
  ;{:id id, :name name, :plan []}
  {:id id, :name name})

(pprint (new-patient 1 "Victor"))
; Will throw error
;(pprint (new-patient "test" "Victor"))


; This method is just for example purpose. Clojure already has 'pos?' and 'pos-int?' methods
; to do the same thing
(defn strictly-positive? [x]
  (> x 0))

; Creates an schema using a function
(def StrictlyPositive (s/pred strictly-positive? 'strictly-positive))

(println "StrictlyPositive 1: " (s/validate StrictlyPositive 1))
; Will throw an error
;(println "StrictlyPositive 0: " (s/validate StrictlyPositive 0))
; Will throw an error
;(println "StrictlyPositive -1: " (s/validate StrictlyPositive -1))



; Creates a schema to be used later with multiple validation
(def Patient3
  "Schema of a patient"
  {:id (s/constrained s/Int strictly-positive?), :name s/Str})

(println "Validate int + strictly positive: " (s/validate Patient3 {:id 1 :name "Victor"}))
; Will throw an error
;(println "Validate int + strictly positive: " (s/validate Patient3 {:id 0 :name "Victor"}))