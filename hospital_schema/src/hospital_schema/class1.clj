(ns hospital-schema.class1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(defn add-patient [patients patient]
  (if-let [id (:id patient)]
    (assoc patients id patient)
    (throw (ex-info "Patient has no :id field" {:patient patient}))))

(defn add-visit
  [visits, patient, add-visits]
  (if (contains? visits patient)
    (update visits patient concat add-visits)
    (assoc visits patient add-visits)))

(defn print-patient-visit-summary [visits, patient]
  (println "Patient visits" patient "are" (get visits patient)))

(defn test-patients []
  (let [victor {:id 15, :name "Victor"}
        billy {:id 20, :name "Billy"}
        leo {:id 25, :name "Leo"}

        patients (reduce add-patient {} [victor, billy, leo])

        ; Shadowing, not so good (repetition)
        visits {}
        visits (add-visit visits 15 ["2019-02-01"])
        visits (add-visit visits 20 ["2019-02-01", "2020-02-01"])
        visits (add-visit visits 15 ["2019-03-02"])]

    (pprint patients)
    (pprint visits)
    ; Will always work
    (print-patient-visit-summary visits 20)
    ; With the first function definition will return nil because its not the expected parameter.
    ; With the second (validation), will throw an exception
    (print-patient-visit-summary visits victor)))

(test-patients)


(pprint (s/validate Long 15))
; Throws exception because its not Long
;(pprint (s/validate Long "test"))

; Forces an error if the schema is not followed
(s/set-fn-validation! true)
; Creates a function specifying the schema that needs to be followed
(s/defn test-validate [x :- Long])
(test-validate 15)
; Will throw error
; (test-validate "test")

(s/defn print-patient-visit-summary
  [visits, patient :- Long]
  (println "Patient visits" patient "are" (get visits patient)))

(test-patients)


(s/defn new-patient
  [id :- Long, name :- s/Str]
  { :id id, :name name})

; Working
(pprint (new-patient 7 "Victor"))
; Error
(pprint (new-patient "Victor" 7))