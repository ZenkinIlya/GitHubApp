package com.example.githubapp.presentation.common;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersProvider {

  public Scheduler io() {
    return Schedulers.io();
  }

  public Scheduler computation() {
    return Schedulers.computation();
  }

  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}
