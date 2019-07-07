package com.exam.myapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.exam.myapp.entity.IPersonManger;
import com.exam.myapp.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    private List<Person> mPersons = new ArrayList<>();
    private IPersonManger.Stub mManger = new IPersonManger.Stub() {
        @Override
        public List<Person> getPersons() throws RemoteException {
            return mPersons;
        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            mPersons.add(person);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mManger;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mManger.addPerson(new Person("Li Lei", 14, "Male"));
            mManger.addPerson(new Person("Lily", 13, "Female"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
