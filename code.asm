.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
main_var3 DD ?
main_var2 DD ?
_12 DD 12
main_var1 DD ?
NODIO7 DB "NO DIO 7",0
TERMINOELPROGRAMACORRECTAMENTE DB "TERMINO EL PROGRAMA CORRECTAMENTE",0
_3 DD 3
DIO7 DB "DIO 7",0
_2 DD 2
_1 DD 1
_10 DD 10
_65535 DD 65535
_0 DD 0
_6 DD 6
_5 DD 5
_4 DD 4
_8 DD 8
main_count DD ?
 _OFmsg DB "OVERFLOW ",0
rtn DD ?
.code
start:
MOV EBX , _65535
MOV main_var1 , EBX
MOV EBX , _1
MOV main_var2 , EBX
MOV EBX , main_var1
ADD EBX , main_var2
CMP EBX , 65535
JG _overflowed
MOV main_var3 , EBX
invoke MessageBox, NULL, addr TERMINOELPROGRAMACORRECTAMENTE ,addr TERMINOELPROGRAMACORRECTAMENTE ,MB_ICONINFORMATION
invoke ExitProcess, 0
_overflowed:
invoke MessageBox, NULL, addr _OFmsg ,addr _OFmsg ,MB_ICONERROR
invoke ExitProcess, 0
end start
