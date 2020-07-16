(ns hospital-schema.class4
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; Forces an error if the schema is not followed
(s/set-fn-validation! true)

; Just a shortcut because we are using in more than one place
(def PosInt (s/pred pos-int? 'positive-integer))
(def Plan [s/Keyword])
(def Patient
  "Schema of a patient with plan"
  {:id PosInt,
   :name s/Str,
   :plan Plan
   (s/optional-key :birth) s/Str})

(println "Patient ok: " (s/validate Patient {:id 1 :name "Victor" :plan [:x-ray]}))
(println "Patient ok 2: " (s/validate Patient {:id 1 :name "Victor" :plan [] :birth "2020-01-01"}))


; Using a different type of map
(def Patients {PosInt Patient})

(println "Patients empty: " (s/validate Patients {}))
(let [victor {:id 1 :name "Victor" :plan [:x-ray]}]
  (println "Patients victor: " (s/validate Patients {1 victor}))
  ; Will throw an error
  ;(println "Patients negative: " (s/validate Patients {-1 victor}))
  ; Will throw an error
  ;(println "Patients wrong patient: " (s/validate Patients {1 -1}))
  ; Will throw an error
  ;(println "Patients invalid Patient: " (s/validate Patients {1 {:id 1}}))
  )