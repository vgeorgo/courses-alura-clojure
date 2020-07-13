(ns hospital-patient.model)

; From Sean Devlin (https://github.com/seancorfield) examples
(defprotocol Datable
  (to-ms [this]))

(extend-type java.lang.Number
  Datable
  (to-ms [this] this))

(extend-type java.util.Date
  Datable
  (to-ms [this] (.getTime this)))

(extend-type java.util.Calendar
  Datable
  (to-ms [this] (to-ms (.getTime this))))