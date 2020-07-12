(ns hospital-patient.class2
  (:use clojure.pprint))

; Defining protocols (similar to interfaces)
(defprotocol PatientAuthorization
  (sign-authorization? [patient procedure value]))

; Defining records (classes)
(defrecord HealthInsurancePatient [id name document plan])
(defrecord PrivatePatient [id name document]
  ; Interface can be implemented directly or using 'extend-type'
  ;PatientAuthorization
  ;(sign-authorization? [patient procedure value]
  ;  (>= value 50))
  )

; Implementing protocols methods for each type
(extend-type PrivatePatient
  PatientAuthorization
  (sign-authorization? [patient procedure value]
    (>= value 50)))

(extend-type HealthInsurancePatient
  PatientAuthorization
  (sign-authorization? [patient procedure value]
    (let [plan (:plan patient)]
      (not (some #(= % procedure) plan)))))


(let [private-patient (->PrivatePatient 1 "Victor" "123456")
      plan-patient (->HealthInsurancePatient 2 "Fred" "987654" [:x-ray :ultrasound])]
  (println "Private patient sign 500? " (sign-authorization? private-patient :x-ray 500))
  (println "Private patient sign 40? " (sign-authorization? private-patient :x-ray 40))

  (println "Plan patient sign x-ray? " (sign-authorization? plan-patient :x-ray 500))
  (println "Plan patient sign weird-exam? " (sign-authorization? plan-patient :weird-exam 500)))


; From Sean Devlin examples
(defprotocol Datable
  (to-ms [this]))

(extend-type java.lang.Number
  Datable
  (to-ms [this] this))

(println "Test Number: " (to-ms 56))

(extend-type java.util.Date
  Datable
  (to-ms [this] (.getTime this)))

(println "Test Date: " (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
  Datable
  (to-ms [this] (to-ms (.getTime this))))

(println "Test Calendar: " (to-ms (java.util.GregorianCalendar.)))