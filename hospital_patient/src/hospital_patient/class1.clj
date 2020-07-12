(ns hospital-patient.class1
  (:use clojure.pprint))

(defn add-patient
  "Add a patient to a list of patients. Throw exception if :id id not provided"
  [patients patient]
  (if-let [id (:id patient)]
    (assoc patients id patient)
    (throw (ex-info "Patient :id not provided" {:patient patient}))))

(defn test-add-patients []
  (let [patients {}
        victor {:id 1 :name "Victor"}
        leo {:id 1 :name "Leo"}
        billy {:name "Billy"}]

    (pprint (add-patient patients victor))
    (pprint (add-patient patients leo))
    (pprint (add-patient patients billy))))

; (test-add-patients)


; Behind the scenes Patient becomes a class in Java
(defrecord Patient [id, name])

(pprint (->Patient 1 "Fred"))
(pprint (Patient. 1 "Fred"))
(pprint (map->Patient {:id 1 :name "Fred"}))

(let [fred (->Patient 1 "Fred")
      ; Can define extra/less arguments
      peter (map->Patient {:id 1 :name "Peter" :document "123456"})]

  ; Can be used like a map
  (pprint (:id fred))
  (pprint (vals fred))
  (println "Is record? " (record? fred))
  ; Used as a property (faster accessing directly)
  (println "Name: " (.name fred))
  (pprint peter)

  ; Still immutable, return a new Record with updated values
  (pprint (assoc fred :id 20))

  ; Comparison
  (println "Compare Fred: " (= fred (->Patient 1 "Fred")))
  (println "Compare different: " (= fred (->Patient 1 "Peter"))))