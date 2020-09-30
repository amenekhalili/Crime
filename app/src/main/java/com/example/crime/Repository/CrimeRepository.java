package com.example.crime.Repository;

import com.example.crime.Model.Crime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeRepository {
    private static final int CRIME_SIZE = 100 ;
   private static CrimeRepository sIsInstance;

    public static CrimeRepository getIsInstance() {

        if(sIsInstance == null)
            sIsInstance = new CrimeRepository();

        return sIsInstance;
    }


    private List<Crime> mCrimes;

    private CrimeRepository(){
        mCrimes = new ArrayList<>();
        for (int i = 0; i < CRIME_SIZE; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime# " + (i+1));
            crime.setSolved( i % 2 == 0);

            mCrimes.add(crime);
        }

    }
    public static void setIsInstance(CrimeRepository isInstance) {
        sIsInstance = isInstance;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getcrime(UUID id){
        for (Crime crime: mCrimes) {
            if(crime.getId().equals(id))
                return crime;

        }
        return null;
    }

    public void insertCrime(Crime crime){
        mCrimes.add(crime);
    }
    //TODO
    public void deleteCrime(Crime crime){

    }
    //TODO
    public void updateCrime(Crime crime){

    }
}
