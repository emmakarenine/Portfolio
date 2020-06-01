#pragma once
#ifndef DATA_PROCESSOR_H
#define DATA_PROCESSOR_H


#include "timed_processor.h"
#include "data.h"


class data_processor : public timed_processor<data>
{
public:
    data_processor() = default;
    data_processor(data_processor const &) = default;
    data_processor(data_processor &&) = default;
    ~data_processor() override = default;
    data_processor& operator=(data_processor const &) = default;
    data_processor& operator=(data_processor &&) = default;
};


#endif // DATA_PROCESSOR_H