uniform vec2  myUniformArray[5];
uniform float myUniformFloat;
uniform vec2  myUniformVector2f;
uniform int   myUniformInt;

void main(void){  
    gl_FragColor = vec4(myUniformArray[2],
                        myUniformVector2f.x,
                        myUniformFloat*myUniformInt);
}