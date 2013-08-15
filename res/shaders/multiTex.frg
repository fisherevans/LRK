varying vec4 gl_FrontColor;
uniform sampler2D colorMap, normalMap;
uniform vec2 lighting;


void main (void){   
  vec3 lightVec = vec3((gl_TexCoord[0].st-lighting.xy), 0.25);
  float distSqr = dot(lightVec, lightVec);
  vec3 lVec = lightVec * inversesqrt(distSqr);

  vec4 base = texture2D(colorMap, gl_TexCoord[0].st);
  
  vec3 bump = normalize( texture2D(normalMap, gl_TexCoord[0].st).xyz * 2.0 - 1.0);
  
  float diffuse = max( dot(lVec, bump), 0.0 );
  vec3 vdiffuse = vec3(1.0,0.98,0.98)*diffuse;
   
  base.rgb = base.rgb*vdiffuse;
  gl_FragColor = base * gl_FrontColor;
}