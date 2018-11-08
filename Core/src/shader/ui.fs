#version 400 core

in vec3 outPosition;
in vec2 outTextureCoordinates;

out vec4 outColor;

uniform sampler2D diffuseTexture;

void main() {
    vec4 tex = texture(diffuseTexture, outTextureCoordinates);

    outColor = tex;
}