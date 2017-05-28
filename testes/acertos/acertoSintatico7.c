#include <stdio.h>
#include <string.h>

int main(){
int var1, guess, num;
string str1;
, boolean, achou;
var1 = 0;
guess = 0;
num = 59;
strcpy(str1 ," Henry ");
achou = False;
printf(" Well ,  %s ",," , I am thinking of a n between between and and .  %s ");
for(var1=0;var1<50;var1++){
if(guess< num){
printf(" Your guess is too low .  ");
guess = guess*2;

}else{
if(guess> num){
printf(" Your guess is too high .  ");
guess = guess/2;

}else{
printf(" Yes , I am thinking about  %d ",guess);
achou = True;
break;
}
}
}if(notachou){
printf(" Nope . The number I was thinking of was  %d ",num);

}
return 0;
}
