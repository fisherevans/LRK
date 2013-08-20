#version 120

uniform sampler2D tex0;
uniform float resolution;
uniform float radius;

void main() {
        vec4 sum = vec4(0.0);
        vec2 tc = gl_TexCoord[0].st;
        float blur = radius/resolution;

        sum += texture2D(tex0, vec2(tc.x - 8.0*blur, tc.y)) * 0.00098;
        sum += texture2D(tex0, vec2(tc.x - 7.0*blur, tc.y)) * 0.00343;
        sum += texture2D(tex0, vec2(tc.x - 6.0*blur, tc.y)) * 0.00980;
        sum += texture2D(tex0, vec2(tc.x - 5.0*blur, tc.y)) * 0.02328;
        sum += texture2D(tex0, vec2(tc.x - 4.0*blur, tc.y)) * 0.04656;
        sum += texture2D(tex0, vec2(tc.x - 3.0*blur, tc.y)) * 0.07915;
        sum += texture2D(tex0, vec2(tc.x - 2.0*blur, tc.y)) * 0.11513;
        sum += texture2D(tex0, vec2(tc.x - 1.0*blur, tc.y)) * 0.14391;

        sum += texture2D(tex0, vec2(tc.x, tc.y)) * 0.15498;

        sum += texture2D(tex0, vec2(tc.x + 1.0*blur, tc.y)) * 0.14391;
        sum += texture2D(tex0, vec2(tc.x + 2.0*blur, tc.y)) * 0.11513;
        sum += texture2D(tex0, vec2(tc.x + 3.0*blur, tc.y)) * 0.07915;
        sum += texture2D(tex0, vec2(tc.x + 4.0*blur, tc.y)) * 0.04656;
        sum += texture2D(tex0, vec2(tc.x + 5.0*blur, tc.y)) * 0.02328;
        sum += texture2D(tex0, vec2(tc.x + 6.0*blur, tc.y)) * 0.00980;
        sum += texture2D(tex0, vec2(tc.x + 7.0*blur, tc.y)) * 0.00343;
        sum += texture2D(tex0, vec2(tc.x + 8.0*blur, tc.y)) * 0.00098;

        gl_FragColor = vec4(sum.rgba);
}