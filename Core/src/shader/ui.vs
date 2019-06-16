#version 400 core

in vec3 position;
in vec2 textureCoordinates;

out vec3 outPosition;
out vec2 outTextureCoordinates;

uniform mat4 transformationMatrix;

uniform mat4 layerMatrix;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main() {
    vec4 worldPosition = layerMatrix * transformationMatrix * vec4(position, 1.0);

    outPosition = worldPosition.xyz;
    outTextureCoordinates = textureCoordinates;

    gl_Position = projectionMatrix * viewMatrix * worldPosition;
}