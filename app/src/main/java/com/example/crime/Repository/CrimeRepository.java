package com.example.crime.Repository;

import com.example.crime.Model.Crime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeRepository implements IRepository{
    private static final int CRIME_SIZE = 2 ;
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
     @Override
    public List<Crime> getCrimes() {
        return mCrimes;
    }
   @Override
    public int sizeList(){
        return mCrimes.size();
    }

    @Override
    public Crime getCrime(UUID uuid) {
        for (Crime crime: mCrimes) {
            if(crime.getId().equals(uuid))
                return crime;
        }
        return null;
    }

@Override
    public void insertCrime(Crime crime){
        mCrimes.add(crime);
    }
  @Override
    public void deleteCrime(Crime crime){
      for (int i = 0; i <mCrimes.size() ; i++) {
          if(mCrimes.get(i).getId().equals(crime.getId())){
              mCrimes.remove(i);
              return;
          }
      }

    }
   @Override
    public void updateCrime(Crime crime){
        Crime findCrime = getCrime(crime.getId());
        findCrime.setTitle(crime.getTitle());
        findCrime.setSolved(crime.isSolved());
        findCrime.setDate(crime.getDate());

    }
@Override
    public int getPosition(UUID id){
        for (int i = 0; i <mCrimes.size() ; i++) {
            if(mCrimes.get(i).getId().equals(id)){
                return i;
            }

        }
        return -1;
    }

    @Override
    public Crime getCrime(int index) {
        for (int i = 0; i < mCrimes.size() ; i++) {
            return mCrimes.get(index);
        }
        return null;
    }
}
