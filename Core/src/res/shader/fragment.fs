#version 150

in vec3 outPositions;
in vec2 outTextureCoordinates;

out vec4 outColor;

uniform sampler2D diffuseTexture;

void main() {
    outColor = texture(diffuseTexture, outTextureCoordinates);
}