#version 120

uniform sampler2D tex0;
uniform float resolution;
uniform float radius;

void main() {
        vec4 sum = vec4(0.0);
        vec2 tc = gl_TexCoord[0].st;
        float blur = radius/resolution;

        sum += texture2D(tex0, vec2(tc.x, tc.y - 8.0*blur)) * 0.00098;
        sum += texture2D(tex0, vec2(tc.x, tc.y - 7.0*blur)) * 0.00343;
        sum += texture2D(tex0, vec2(tc.x, tc.y - 6.0*blur)) * 0.00980;
        sum += texture2D(tex0, vec2(tc.x, tc.y - 5.0*blur)) * 0.02328;
        sum += texture2D(tex0, vec2(tc.x, tc.y - 4.0*blur)) * 0.04656;
        sum += texture2D(tex0, vec2(tc.x, tc.y - 3.0*blur)) * 0.07915;
        sum += texture2D(tex0, vec2(tc.x, tc.y - 2.0*blur)) * 0.11513;
        sum += texture2D(tex0, vec2(tc.x, tc.y - 1.0*blur)) * 0.14391;

        sum += texture2D(tex0, vec2(tc.x, tc.y)) * 0.15498;

        sum += texture2D(tex0, vec2(tc.x, tc.y + 1.0*blur)) * 0.14391;
        sum += texture2D(tex0, vec2(tc.x, tc.y + 2.0*blur)) * 0.11513;
        sum += texture2D(tex0, vec2(tc.x, tc.y + 3.0*blur)) * 0.07915;
        sum += texture2D(tex0, vec2(tc.x, tc.y + 4.0*blur)) * 0.04656;
        sum += texture2D(tex0, vec2(tc.x, tc.y + 5.0*blur)) * 0.02328;
        sum += texture2D(tex0, vec2(tc.x, tc.y + 6.0*blur)) * 0.00980;
        sum += texture2D(tex0, vec2(tc.x, tc.y + 7.0*blur)) * 0.00343;
        sum += texture2D(tex0, vec2(tc.x, tc.y + 8.0*blur)) * 0.00098;

        gl_FragColor = vec4(sum.rgba);
}