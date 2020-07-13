(ns hospital-patient.class3
  (:use clojure.pprint)
  (:require [hospital-patient.logic :as h.logic]))

(defn find-patient
  "Find a patient, simulating load time with thread sleep"
  [id]
  (println "Loading patient: " id)
  (Thread/sleep 1000)
  {:id id :time-loaded (h.logic/now)})

(defn load-from-cache
  "If its not cached, loads and return the patient"
  [cache id loader]
  (if (contains? cache id)
    cache
    (let [patient (loader id)]
      (assoc cache id patient))))

;(pprint (load-from-cache {}, 15, find-patient))
;(pprint (load-from-cache { 15 { :id 15} }, 15, find-patient))


(defprotocol Loadable
  (load! [this id]))

(defrecord Cache [cache loader]
  Loadable
  (load! [this id]
    (swap! cache load-from-cache id loader)
    (get @cache id)))

(let [patients (->Cache (atom {}) find-patient)]
  (load! patients 1)
  (load! patients 10)
  (load! patients 1)

  (pprint patients))