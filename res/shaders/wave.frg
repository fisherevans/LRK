/* Fragment shader */
uniform sampler2D tex;

uniform float offset;

void main(){
  vec2 loc = gl_TexCoord[0].st;
  loc.x = loc.x + cos(loc.y + offset)/2.0;
  vec4 color = texture2D(tex,loc.xy);
  if(color.rgb == vec3(0.0,0.0,0.0)){
    color.a = 0.0;
  }
  gl_FragColor = color; 
}
