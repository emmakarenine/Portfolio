#pragma once
#ifndef TIMED_PROCESSOR_H
#define TIMED_PROCESSOR_H


#include "processor.h"
#include "execution_timer.h"


template <typename T>
class timed_processor : public processor<T>
{
public:
    using timer_type = execution_timer<>::timer_type;

    timed_processor() = default;
    timed_processor(timed_processor const &) = default;
    timed_processor(timed_processor &&) = default;
    ~timed_processor() override = default;
    timed_processor& operator=(timed_processor const &) = default;
    timed_processor& operator=(timed_processor &&) = default;

    void process(T const & d) final;

    timer_type elapsed_time() const;
    double elapsed_second() const;

protected:
    virtual void do_process(T const & d) = 0;

private:
    timer_type _elapsed_time;
};











#include "enable_high_priority_thread.h"


template<typename T>
inline void timed_processor<T>::process(T const & d)
{
    enable_high_priority_thread high_priority_thread_enabled;

    _elapsed_time = execution_timer<>::evaluate([&, this]{ do_process(d); });
}

template<typename T>
inline typename timed_processor<T>::timer_type timed_processor<T>::elapsed_time() const
{
    return _elapsed_time;
}

template<typename T>
inline double timed_processor<T>::elapsed_second() const
{
    return elapsed_time() * execution_timer<>::ratio;
}


#endif // TIMED_PROCESSOR_H