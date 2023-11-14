.section .data 
	
.section .text
	.global mediana

mediana:
	pushq %rdi
	pushq %rsi
	call sort_array
	popq %rsi
	popq %rdi

	movl %esi, %eax
	cdq
	movl $2, %ecx
	idivl %ecx
	
	movl %eax, %edx
	movl (%rdi, %rdx, 4), %eax	
	ret
	
	
	
	
