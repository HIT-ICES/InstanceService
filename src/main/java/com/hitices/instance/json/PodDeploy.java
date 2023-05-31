package com.hitices.instance.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/31
 */
@Getter
@Setter
@NoArgsConstructor
public class PodDeploy {
    @JsonProperty("apiVersion")
    private String apiVersion;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("metadata")
    private Metadata metadata;

    @JsonProperty("spec")
    private Spec spec;

    // Getter and Setter methods for all fields

    public static class Metadata {
        @JsonProperty("namespace")
        private String namespace;

        @JsonProperty("labels")
        private Labels labels;

        @JsonProperty("name")
        private String name;

        @JsonProperty("annotations")
        private Annotations annotations;

        // Getter and Setter methods for all fields

        public static class Labels {
            @JsonProperty("app")
            private String app;

            // Getter and Setter methods for "app" field
        }

        public static class Annotations {
            @JsonProperty("kubesphere.io/alias-name")
            private String aliasName;

            @JsonProperty("kubesphere.io/description")
            private String description;

            @JsonProperty("kubesphere.io/creator")
            private String creator;

            // Getter and Setter methods for all fields
        }
    }

    public static class Spec {
        @JsonProperty("replicas")
        private int replicas;

        @JsonProperty("selector")
        private Selector selector;

        @JsonProperty("template")
        private Template template;

        @JsonProperty("strategy")
        private Strategy strategy;

        // Getter and Setter methods for all fields

        public static class Selector {
            @JsonProperty("matchLabels")
            private MatchLabels matchLabels;

            // Getter and Setter methods for "matchLabels" field

            public static class MatchLabels {
                @JsonProperty("app")
                private String app;

                // Getter and Setter methods for "app" field
            }
        }

        public static class Template {
            @JsonProperty("metadata")
            private Metadata metadata;

            @JsonProperty("spec")
            private ContainerSpec spec;

            // Getter and Setter methods for all fields

            public static class Metadata {
                @JsonProperty("labels")
                private Labels labels;

                @JsonProperty("annotations")
                private Annotations annotations;

                // Getter and Setter methods for all fields

                public static class Labels {
                    @JsonProperty("app")
                    private String app;

                    // Getter and Setter methods for "app" field
                }

                public static class Annotations {
                    @JsonProperty("kubesphere.io/imagepullsecrets")
                    private String imagePullSecrets;

                    @JsonProperty("kubesphere.io/creator")
                    private String creator;

                    // Getter and Setter methods for all fields
                }
            }

            public static class ContainerSpec {
                @JsonProperty("containers")
                private Container[] containers;

                @JsonProperty("serviceAccount")
                private String serviceAccount;

                @JsonProperty("initContainers")
                private Object[] initContainers;

                @JsonProperty("volumes")
                private Object[] volumes;

                @JsonProperty("imagePullSecrets")
                private Object imagePullSecrets;

                // Getter and Setter methods for all fields

                public static class Container {
                    @JsonProperty("name")
                    private String name;

                    @JsonProperty("imagePullPolicy")
                    private String imagePullPolicy;

                    @JsonProperty("ports")
                    private Port[] ports;

                    @JsonProperty("image")
                    private String image;

                    // Getter and Setter methods for all fields

                    public static class Port {
                        @JsonProperty("name")
                        private String name;

                        @JsonProperty("protocol")
                        private String protocol;

                        @JsonProperty("containerPort")
                        private int containerPort;

                        // Getter and Setter methods for all fields
                    }
                }
            }
        }

        public static class Strategy {
            @JsonProperty("type")
            private String type;

            @JsonProperty("rollingUpdate")
            private RollingUpdate rollingUpdate;

            // Getter and Setter methods for all fields

            public static class RollingUpdate {
                @JsonProperty("maxUnavailable")
                private String maxUnavailable;

                @JsonProperty("maxSurge")
                private String maxSurge;

                // Getter and Setter methods for all fields
            }
        }
    }
}
