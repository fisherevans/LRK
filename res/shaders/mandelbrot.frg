
void main (void)
{
  float iteration = 0.0;
  vec2 c = gl_TexCoord[0].xy;
  vec2 z = c;

  while(dot(z,z) <= 4.0 && iteration < 250.0){
    z = vec2(z.x*z.x - z.y*z.y, 2.0*z.x*z.y) + c;
    
    iteration++;
  }
  
  
  float mu = iteration/250.0;
  
  if(dot(z,z) < 4.0){
    gl_FragColor = vec4 (0.0, 0.0, 0.0, 1.0);
  }else if(iteration < 80.0){
    gl_FragColor = vec4 (0.2+mu, 0.25+mu*1.1, 0.2, 1.0);
  }else if(iteration < 160.0){
    gl_FragColor = vec4 (0.25, 0.2+mu, 0.2, 1.0);
  }else{
    gl_FragColor = vec4 (0.25+mu, 0.25+mu/2.0, 0.2, 1.0);
  }
}
