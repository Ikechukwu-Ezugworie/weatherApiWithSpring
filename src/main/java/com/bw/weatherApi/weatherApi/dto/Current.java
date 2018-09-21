package com.bw.weatherApi.weatherApi.dto;

public class Current {

        private String temp_c;
        private String temp_f;

        public Current() {
        }

        public String getTemp_c() {
            return temp_c;
        }

        public void setTemp_c(String temp_c) {
            this.temp_c = temp_c;
        }

        public String getTemp_f() {
            return temp_f;
        }

        public void setTemp_f(String temp_f) {
            this.temp_f = temp_f;
        }

    @Override
    public String toString() {
        return "Current{" +
                "temp_c='" + temp_c + '\'' +
                ", temp_f='" + temp_f + '\'' +
                '}';
    }
}
