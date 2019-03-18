#include <math.h>

void schaffer(double* vars, double* objs) {
	objs[0] = pow(vars[0], 2.0);
	objs[1] = pow(vars[0] - 2.0, 2.0);
}
