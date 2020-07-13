(ns hospital-patient.logic
  (:require [hospital-patient.model :as h.model]))

(defn now []
  (h.model/to-ms (java.util.Date.)))