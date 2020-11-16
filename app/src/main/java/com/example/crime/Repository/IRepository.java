package com.example.crime.Repository;

import com.example.crime.Model.Crime;

import java.util.List;
import java.util.UUID;

public interface IRepository {
    List<Crime>  getCrimes();
    Crime getCrime(UUID uuid);
    void insertCrime(Crime crime);
    void updateCrime(Crime crime);
    void deleteCrime(Crime crime);
    int getPosition(UUID id);
    //Crime getCrime(int index);
    int sizeList();
}
