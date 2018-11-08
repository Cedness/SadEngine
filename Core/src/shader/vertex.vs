#version 150

in vec3 positions;
in vec2 textureCoordinates;

out vec3 outPositions;
out vec2 outTextureCoordinates;

uniform vec3 offset;

void main() {
    outPositions = positions + offset;
    outTextureCoordinates = textureCoordinates;
    gl_Position = vec4(outPositions, 1);
}