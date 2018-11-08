#version 400 core

in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;

out vec3 outPosition;
out vec2 outTextureCoordinates;

out vec3 outNormal;
out vec3 outToLight;

out vec3 outToCamera;

uniform mat4 transformationMatrix;

uniform mat4 frameMatrix;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform vec3 lightPosition;

void main() {
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);

    outPosition = worldPosition.xyz;
    outTextureCoordinates = textureCoordinates;

    gl_Position = projectionMatrix * viewMatrix * frameMatrix * worldPosition;

    outNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
    outToLight = lightPosition - worldPosition.xyz;

    outToCamera = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
}