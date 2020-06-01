#include "b52p2_PhouttamaMakraphone_1.h"

#include <string>		// required for std::string

std::string const & b52p2_PhouttamaMakraphone_1::implementation_description()
{
	static const std::string mDesc("Implementation #1 : version simple");
	return mDesc;
}

std::string const & b52p2_PhouttamaMakraphone_1::implementation_id()
{
	static const std::string mImplemID("0");
	return mImplemID;
}

std::string const & b52p2_PhouttamaMakraphone_1::author_name()
{
	static const std::string mAuthor("Makraphone Phouttama");
	return mAuthor;
}

std::string const & b52p2_PhouttamaMakraphone_1::author_id()
{
	static const std::string mAuthorID("0446207");
	return mAuthorID;
}


void b52p2_PhouttamaMakraphone_1::do_process(data const & d)
{
	if (d.size() == 0)
	{
		_statistical_metric.set_to_zero_data();
	}
	else
	{
		mMin = process_minimum(d);
		mMax = process_maximum(d);
		mSpan = process_span(d);
		mMedian = process_median(d);
		mMode = process_integer_mode(d);
		mCount = process_count(d);
		mSum = process_sum(d);
		mAverage = process_average(d);
		mVariance = process_variance(d);
		mStandDev = process_standard_deviation(d);

		_statistical_metric.set(mMin,
								mMax,
								mSpan,
								mMedian,
								mIntMode,
								mCount,
								mSum,
								mAverage,
								mVariance,
								mStandDev );
	}

}

const real b52p2_PhouttamaMakraphone_1::process_minimum(data const & d)
{	
	if (d.size() > 0)
	{
		real min = d[0];

		if (d.size() > 1)
		{
			for (auto it{ d.begin() + 1 }; it != d.end(); ++it)
			{
				if (*it < min)
				{
					min = *it;
				}
			}			
		}

		return min;
	}	

	return real(0.0); // envoie 0 par defaut
	
}

const real b52p2_PhouttamaMakraphone_1::process_maximum(data const & d)
{
	if (d.size() > 0)
	{
		real max = d[0];

		if (d.size() > 1)
		{
			for (auto it{ d.begin() + 1 }; it != d.end(); ++it)
			{
				if (*it > max)
				{
					max = *it;
				}
			}			
		}

		return max;
	}

	return real(0.0);
}

const real b52p2_PhouttamaMakraphone_1::process_span(data const & d) // etendue
{
	return mMax - mMin;
}

// Swap DES VALEURS DANS LE TABLEAU
void b52p2_PhouttamaMakraphone_1::Swap(data & toBeSorted, int index1, int index2)
{
	if (index1 < toBeSorted.size())
	{
		real temp{ toBeSorted[index1] };
		toBeSorted[index1] = toBeSorted[index2];
		toBeSorted[index2] = temp;
	}
	
}


int b52p2_PhouttamaMakraphone_1::Partition(data & toBeSorted, int leftIndex, int rightIndex)
{
	int pivot = leftIndex; // Choix du 1er element
	int	i{ leftIndex };
	int	j{ rightIndex };

	while (i < toBeSorted.size() && (toBeSorted[i] <= toBeSorted[pivot]))
		i++;

	while (j >= 0 && (toBeSorted[j] > toBeSorted[pivot]))
		j--;

	if (i >= j) // si l'index de rightIndex finit par rejoindre celui de leftIndex
	{
		Swap(toBeSorted, i, j);
	}

	return pivot;
}

// QUICKSORT RECURSIF
data b52p2_PhouttamaMakraphone_1::QuickSort(data const & toBeSorted, int leftIndex, int rightIndex)
{
	data beingSorted = toBeSorted;

	if (leftIndex == rightIndex) // pour un tableau a un seul element
	{
		;
	}
	else	// pour un tableau a plusieurs elements
	{
		// PARTIE EPROUVEE
		int i{leftIndex};
		int j{ rightIndex },
		pivot = Partition(beingSorted, i, j);

		QuickSort(beingSorted, i, pivot);

		QuickSort(beingSorted, pivot + 1, j);
	}

	return beingSorted;

}


const real b52p2_PhouttamaMakraphone_1::process_median(data const & d)
{
	if (d.size() == 1)
	{
		return d[0];
	}

	data sortedVec{ QuickSort(d, 0, d.size() - 1) };
	int centerIndex{ d.size() / 2 };

	if (centerIndex % 2 == 0)
	{
		real val1{ sortedVec[centerIndex-1] };
		real val2{ sortedVec[centerIndex] };

		return (val1 + val2) / real(2.0);
	}
	else
	{
		return sortedVec[centerIndex];
	}	
}

const integer b52p2_PhouttamaMakraphone_1::process_integer_mode(data const & d)
{
	integer tempValue;
	int tempCount;
	std::vector<std::pair<integer, int> > coupleList{};

	for (real const & val : d) // const pcq on est a l'adresse, et on veut/peut juste lire
	{
		bool processed = false;
		integer currVal(val.round());
	
		for (auto & couple : coupleList) // a l'adresse pour modifier
		{
			if (couple.first == currVal )
			{
				++couple.second;
				processed = true;
			}
		}

		if (!processed)
		{
			coupleList.push_back(std::pair<integer, int>(currVal, 1) );
		}
	}

	tempValue = coupleList[0].first;
	tempCount = coupleList[0].second;

	for (auto const & couple : coupleList)
	{
		if (couple.second > tempCount)
		{
			tempValue = couple.first;
			tempCount = couple.second;
		}
	}
	
	return tempValue;
}

const integer b52p2_PhouttamaMakraphone_1::process_count(data const & d)
{
	return integer(d.size());
}

const real b52p2_PhouttamaMakraphone_1::process_sum(data const & d)
{
	real sum;

	for (auto it{ d.begin() }; it != d.end(); ++it)
	{
		sum += *it;
	}

	return sum;
}

const real b52p2_PhouttamaMakraphone_1::process_average(data const & d)
{
	return mSum/mCount;
}

const real b52p2_PhouttamaMakraphone_1::process_variance(data const & d)
{
	real variance = real();

	for (real const & val : d)
	{
		variance += (val - mAverage).square();
	}

	return variance / mCount;
}

const real b52p2_PhouttamaMakraphone_1::process_standard_deviation(data const & d)
{
	return mVariance.sqrt();
}