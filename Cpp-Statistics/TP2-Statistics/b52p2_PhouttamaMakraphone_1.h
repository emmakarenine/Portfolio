#pragma once

#ifndef B52P2_PHOUTTAMA_MAKRAPHONE_1
#define B52P2_PHOUTTAMA_MAKRAPHONE_1

#include "..\dev\header\data_statistics.h"
#include <string>

class b52p2_PhouttamaMakraphone_1 :	public data_statistics
{
public:
	b52p2_PhouttamaMakraphone_1() = default;
	b52p2_PhouttamaMakraphone_1(b52p2_PhouttamaMakraphone_1 const &) = default;
	~b52p2_PhouttamaMakraphone_1() override = default;

	std::string const & implementation_description() override;
	std::string const & implementation_id() override;
	std::string const & author_name() override;
	std::string const & author_id() override;

protected:
	void do_process(data const & d) override; // data est un std::vector(real)

private:
	const real process_minimum(data const & d);
	const real process_maximum(data const & d);
	const real process_span(data const & d);
	void Swap(data & toBeSorted, int index1, int index2);
	int Partition(data & toBeSorted, int gauche, int droite);
	data QuickSort(data const & toBeSorted, int gauche, int droite);
	const real process_median(data const & d);
	const integer process_integer_mode(data const & d);
	const integer process_count(data const & d);
	const real process_sum(data const & d);
	const real process_average(data const & d);
	const real process_variance(data const & d);
	const real process_standard_deviation(data const & d);

	real mMin;
	real mMax;
	real mSpan;
	real mMedian;
	real mMode;
	real mIntMode;
	real mCount; 
	real mSum;
	real mAverage;
	real mVariance;
	real mStandDev;

};


#endif B52P2_PHOUTTAMA_MAKRAPHONE_1
