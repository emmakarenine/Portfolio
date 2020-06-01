#pragma once


#include <string>


template <typename T>
class processor
{
public:
    processor() = default;
    processor(processor const &) = default;
    processor(processor &&) = default;
    virtual ~processor() = default;
    processor& operator=(processor const &) = default;
    processor& operator=(processor &&) = default;

    virtual std::string const & process_description() = 0;
    virtual std::string const & implementation_description() = 0;
    virtual std::string const & implementation_id() = 0;
    virtual std::string const & author_name() = 0;
    virtual std::string const & author_id() = 0;

    virtual void process(T const & d) = 0;
};

