(ns hospital-schema.class5
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

(def Patients {PosInt Patient})
(def Visits {PosInt [s/Str]})

(s/defn add-patient :- Patients
  [patients :- Patients, patient :- Patient]
  (let [id (:id patient)]
    (assoc patients id patient)))

(s/defn add-visit :- Visits
  [visits :- Visits, patient :- PosInt, new-visits :- [s/Str]]
  (if (contains? visits patient)
    (update visits patient concat new-visits)
    (assoc visits patient new-visits)))

(s/defn print-patient-visit-summary [visits :- Visits, patient :- PosInt]
  (println "Patient visits" patient "are" (get visits patient)))

(defn test-patients []
  (let [victor {:id 15, :name "Victor", :plan []}
        billy {:id 20, :name "Billy", :plan []}
        leo {:id 25, :name "Leo", :plan []}

        patients (reduce add-patient {} [victor, billy, leo])

        ; Shadowing, not so good (repetition)
        visits {}
        visits (add-visit visits 15 ["2019-02-01"])
        visits (add-visit visits 20 ["2019-02-01", "2020-02-01"])
        visits (add-visit visits 15 ["2019-03-02"])]

    (pprint patients)
    (pprint visits)
    (print-patient-visit-summary visits 20)))

(test-patients)