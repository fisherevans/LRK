varying vec4 gl_FrontColor;
varying vec3 vVertex;
uniform sampler2D colorMap, normalMap;
uniform vec2 lighting;

void main (void){
  vec3 lightVec = vec3((gl_LightSource[0].position.xy - vVertex.xy), 20);
  float distSqr = dot(lightVec, lightVec);
  float att = clamp(1.0 - 0.0095 * sqrt(distSqr), 0.0, 1.0);
  vec3 lVec = lightVec * inversesqrt(distSqr);

  vec4 base = texture2D(colorMap, gl_TexCoord[0].st);
  
  vec3 bump = normalize( texture2D(normalMap, gl_TexCoord[0].st).xyz * 2.0 - 1.0);
  
  vec4 vAmbient = gl_LightSource[0].ambient * gl_FrontColor;
  
  float diffuse = max( dot(lVec, bump), 0.0 );
  vec4 vDiffuse = gl_LightSource[0].diffuse*diffuse * gl_FrontColor;
  
  float specular = pow(clamp(dot(reflect(-lVec, bump), vec3(0.0,0.0,1.0)), 0.0, 1.0), 
                   gl_FrontMaterial.shininess );
  vec4 vSpecular = gl_LightSource[0].specular * gl_FrontMaterial.specular * 
                   specular;  

  gl_FragColor = ( vAmbient*base + vDiffuse*base + vSpecular) * att;
  gl_FragColor.a = 1.0f;
}