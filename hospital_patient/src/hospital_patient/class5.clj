(ns hospital-patient.class5
  (:use clojure.pprint))

; Returns anything that will be used to identify what method should be used.
; Using :keys to defined what method it will use.
(defn type-of-authorization [request]
  (let [patient (:patient request)
        situation (:situation request)]

    (cond (= :urgent situation) :authorized
          (contains? patient :plan) :heath-insurance
          :else :private)))

; Defines a method that will have multiple implementations depending the result of the function
; 'type-of-authorization'
(defmulti sign-authorization? type-of-authorization)

; Defining the methods based on the possible returns of 'type-of-authorization'
(defmethod sign-authorization? :authorized [_]
  false)

(defmethod sign-authorization? :private [request]
  (>= (:value request 0) 50))

(defmethod sign-authorization? :heath-insurance [request]
  (let [plan (:plan (:patient request))
        procedure (:procedure request)]
    (not (some #(= % procedure) plan))))

; Test methods
(let [private-patient {:id 1 :name "Victor" :document "123456"}
      plan-patient {:id 2 :name "Fred" :document "987654" :plan [:x-ray :ultrasound]}]

  (println
    "Private patient normal sign 500? "
    (sign-authorization? {:patient private-patient, :value 500, :procedure :blood-sample :situation :normal}))
  (println
    "Private patient normal sign 40? "
    (sign-authorization? {:patient private-patient, :value 40, :procedure :blood-sample}))
  (println
    "Private patient urgent sign 500? "
    (sign-authorization? {:patient private-patient, :value 500, :procedure :blood-sample :situation :urgent}))

  (println
    "Plan patient normal sign x-ray? "
    (sign-authorization? {:patient plan-patient, :value 500, :procedure :x-ray :situation :normal}))
  (println
    "Plan patient normal sign weird-exam? "
    (sign-authorization? {:patient plan-patient, :value 500, :procedure :weird-exam :situation :normal}))
  (println
    "Plan patient urgent sign weird-exam? "
    (sign-authorization? {:patient plan-patient, :value 500, :procedure :weird-exam :situation :urgent})))