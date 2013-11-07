.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
MAIN_I DD ?
MAIN_J DD ?
MAIN_N DD ?
MAIN_A DD ?
.code
start:
invoke ExitProcess, 0
end start
