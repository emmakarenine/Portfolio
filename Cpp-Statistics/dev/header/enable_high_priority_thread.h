#pragma once
#ifndef ENABLE_HIGH_PRIORITY_THREAD_H
#define ENABLE_HIGH_PRIORITY_THREAD_H



class enable_high_priority_thread
{
public:
    enable_high_priority_thread();
    ~enable_high_priority_thread();

private:
    int _thread_priority;
};



#endif // ENABLE_HIGH_PRIORITY_THREAD_H
