#version 400 core

in vec3 outPosition;
in vec2 outTextureCoordinates;

in vec3 outNormal;
in vec3 outToLight;

in vec3 outToCamera;

out vec4 outColor;

uniform sampler2D diffuseTexture;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main() {
    vec4 tex = texture(diffuseTexture, outTextureCoordinates);

    vec3 normalizedNormal = normalize(outNormal);
    vec3 normalizedToLight = normalize(outToLight);
    vec3 normalizedToCamera = normalize(outToCamera);

    vec3 preLight = lightColor * max(dot(normalizedToLight, normalizedNormal), 0.4);
    vec4 light = vec4(preLight, 1.0);
    vec3 preReflectedLight = lightColor * pow(max(dot(reflect(-normalizedToLight, normalizedNormal), normalizedToCamera), 0.0), shineDamper);
    vec4 reflectedLight = vec4(preReflectedLight, 1.0);

    outColor = tex;
}