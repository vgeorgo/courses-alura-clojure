(ns hospital-schema.class3
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; Forces an error if the schema is not followed
(s/set-fn-validation! true)

; Just a shortcut because we are using in more than one place
(def PosInt (s/pred pos-int? 'positive-integer))

(def Patient
  "Schema of a patient"
  {:id PosInt, :name s/Str})

(s/defn new-patient :- Patient
  [id :- PosInt, name :- s/Str]
  {:id id, :name name})

(println "Patient 1: " (new-patient 1 "Victor"))

(defn gte-zero? [x] (>= x 0))
(def MoneyValue (s/constrained s/Num gte-zero?))

(def Request
  "Request of a patient"
  {:patient Patient, :value MoneyValue, :procedure s/Keyword})

(s/defn new-request :- Request
  [patient :- Patient, value :- MoneyValue, procedure :- s/Keyword]
  {:patient patient, :value value, :procedure procedure})

(println "Request 1: " (new-request (new-patient 1 "Victor") 99.99 :x-ray))



(def NumbersSeq [s/Num])
(println "Sequence numbers int: " (s/validate NumbersSeq [1]))
(println "Sequence numbers with float: " (s/validate NumbersSeq [1, 12.2]))
;Will throw exception
;(println "Sequence numbers vector of nil: " (s/validate NumbersVector [nil]))
(println "Sequence numbers zero: " (s/validate NumbersSeq [0]))
(println "Sequence numbers empty seq: " (s/validate NumbersSeq []))
; nil is considered an empty vector, so it will not throw exception
(println "Sequence numbers nil: " (s/validate NumbersSeq nil))

(def Plan [s/Keyword])

(def Patient2
  "Schema of a patient with plan"
  {:id PosInt, :name s/Str, :plan Plan})

(println "Patient ok: " (s/validate Patient2 {:id 1 :name "Victor" :plan [:x-ray]}))
(println "Patient ok 2: " (s/validate Patient2 {:id 1 :name "Victor" :plan []}))
; :plan is required to exist in the map, but nil is a valid sequence of s/Keyword
(println "Patient ok 3: " (s/validate Patient2 {:id 1 :name "Victor" :plan nil}))