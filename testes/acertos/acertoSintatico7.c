#include <stdio.h>
#include <string.h>

int main(){
int var1, guess, num;
char str1[30];
boolean achou;
var1 = 0;
guess = 0;
num = 59;
strcpy(str1 ," Henry ");
achou = false;
printf(" Well , %s ",str1," , I am thinking of a n between 1 and 100 . ");
for(var1=0;var1<50;var1++){
if(guess< num){
printf(" Your guess is too low . ");
guess = guess*2;

}else{
if(guess> num){
printf(" Your guess is too high . ");
guess = guess/2;

}else{
printf(" Yes , I am thinking about %d ",guess);
achou = true;
break;
}
}
}if(!achou){
printf(" Nope . The number I was thinking of was %d ",num);

}
return 0;
}
