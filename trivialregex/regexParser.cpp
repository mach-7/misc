#include <iostream>
#include <cassert>
#include <stack>
#include <utility>
using namespace std;



class Matcher{

private: 
	// to store ti and ri
	stack<pair<int,int> > myStack;

private:		
	void backtrack(int &a, int &b);
public:	
	match(const char* testString, const char* regexString);	
};

void Matcher :: backtrack(int &a, int&b){
	if(!mystack.empty()){
		pair<int,int> mp = mystack.top();
		mystack.pop();
		a = mp.first;
		b = mp.second;		
	}
}


bool Matcher :: match(const char* testString, const char* regexString){

	int ti = 0 , ri = 0;
		
	// if strings are empty
	if((testString == NULL) || (regexString == NULL) ){
		return false;
	}

	char prev;
	// while the tempString is not comsumed completely
	while(testString[ti] != 0){

			
		if(testString[ti+1] == '*'){
			prev = testString[ti];			
		}


		// compare the characters
		while(testString[ti] == regexString[ri]){
		
			if(testString[ti] == 0){
				break;
			}	
			cout<<testString[ti] << "  "<<regexString[ri]<<endl;
			++ti;
			++ri;			
		}
		
		if(regexString[ri] == '*'){
			// there can be two options
			// either no repeat of previous character
			// or multiple repeats of previous character
			// we have to test for both these conditions	
			// we can go ahead with the first one and put the second on stack
			// and access it if we get a mismatch on the first iteration.
			// once the stack is empty and the outer while gets over, we get a decision
			

						
		} else if(regexString[ri] == '.'){
			// can either ignore the . and increment ri
			// or increment both ri and ti

			
		} else {
			backtrack();
		}
		
		// iterate to next char in testString
		
	}

	if( testString[ti] == 0)
	{	// if the test string is consumed but the regexString is not 
		// then return mismatch
		return regexString[ri] == 0;
	}


	return false;
}

int main(){

	cout<<"case matching \"abcd\" with \"abcd\" \n";
	assert(match("abcd","abcd"));
	cout<<"matched\n";

	cout<<"case matching \"abbbcd\" with \"ab*cd\" \n";
	assert(match("abbbcd","ab*cd"));
	cout<<"matched\n";	

	cout<<"case matching \"ABBBBBBBBkloD\" with \"ABB*BBkl.D\" \n";
	assert(match("ABBBBBBBBkloD","ABB*BBkl.D"));
	
	cout<<"All tests passed.."<<endl;
	return 0;
}

/*
#define MIN(a,b) a<b ? a : b;

    0 1 2 3 4
    A B . K K
    A B K K K

bool match(const char * testString, const char *regexString)
{
    // Check if both the pointers point to valid memory / strings
    if((testString != NULL ) || (regexString != NULL)){
        return False;
    }
    
    // get length of the strings
    int testStringLength = strlen(testString);
    int regexStringLength = strlen(regexString); 
        
    // Now loop through the regexString to match characters with the testString
    
    //int minLen = MIN(testStringLength, regexStringLength);

    bool isMatching = False;
    int i,j = 0 ; // to iterate over regexString
    
    
    for( i = 0 ; (i < testStringLength) && (j < regexStringLength); i++){
        
        char toTest = regexString[j];
        char prev;
        
        if(testString[i] == toTest){
            prev = toTest;
            isMatching = True;
            continue; // continue till end of string for a perfect match or till a special char
        }else{
        
        
   RegexString:     ABB*BBKL
        
      TestString:   ABBBBKL
        
            if( toTest == '*' ){
                // check for 0 case
                // check for reoccurance
                 
                match(testString+ i , RegeXSring+j+1) || prev == testString[i] ? match(testString + i +1, RegesString+j) : false;                 
                               
                if(prev != testString[i]){
                    // 0 occurance case
                    --i; // so that again matching can be done with this character in test String 
                    ++j; // increment to next character in regexString
                    continue;
                } else {
                
                    while((testString[i] == prev) && i < (testStringLength)){
                        ++i; // continue till mismatch
                    }
                    
                    // mismatch found, reset testString pointer, increment regexString pointer
                    --i;
                    ++j;
                    continue;
                }
                              
                
            } else if( toTest == '.'){
               // 0 occurance
               // 1 occurance
               
           bool isSuccess =     match(testString+ i + 1, RegeXSring+j+1) || match(testString + i, RegesString+j+1);
           
           
               
               if(testString[i] == regexString[j+1]){
                   j += 2 ; // move to next to next character in regexstring as next character matches
                   continue;
               } else {
                  // just increment to next character
                  ++j;
                  continue; 
               }             
               
            } else {
                isMatching = False; // mismatch found
                break;    // exit for loop
            }
        }
    }
    
    // what if chars are still left in regexString ??
    return isMatching;
    
}

regexString : * . 

  Example RegexString: ABB*BBkl.D
  testString:          ABBBBBBBkloD
                       ABBBBBBBklD
                       ABBBBklD
                       ABBklD
                       ABBBklD
                       ABBBBklTGPD   

* means 0 or more occurance of previoous character.

. means 0 or 1 occurance of any character.

ABBBBBBklPD

ABBBBklD

ABBPBBlD

ABBBklD

    ABBB*****.DD  * not allowed *
    
    ABB*DFDF*BBB.ddfsfd.GG*fgfg
*/


