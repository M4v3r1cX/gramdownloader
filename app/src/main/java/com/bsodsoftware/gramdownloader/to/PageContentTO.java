package com.bsodsoftware.gramdownloader.to;

import java.util.List;

public class PageContentTO {

    private Graphql graphql;

    public Graphql getGraphql() {
        return graphql;
    }

    public void setGraphql(Graphql graphql) {
        this.graphql = graphql;
    }

    public class Graphql {
        private ShortcodeMedia shortcode_media;

        public ShortcodeMedia getShortcode_media() {
            return shortcode_media;
        }

        public void setShortcode_media(ShortcodeMedia shortcode_media) {
            this.shortcode_media = shortcode_media;
        }
    }

    public class ShortcodeMedia {
        private String __typename;
        private String id;
        private String video_url;
        private EdgeSidecarToChildren edge_sidecar_to_children;

        public String get__typename() {
            return __typename;
        }

        public void set__typename(String __typename) {
            this.__typename = __typename;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public EdgeSidecarToChildren getEdge_sidecar_to_children() {
            return edge_sidecar_to_children;
        }

        public void setEdge_sidecar_to_children(EdgeSidecarToChildren edge_sidecar_to_children) {
            this.edge_sidecar_to_children = edge_sidecar_to_children;
        }
    }

    public class EdgeSidecarToChildren {
        List<Edges> edges;

        public List<Edges> getEdges() {
            return edges;
        }

        public void setEdges(List<Edges> edges) {
            this.edges = edges;
        }
    }

    public class Edges {
        Node node;

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

    public class Node {
        private String id;
        private String video_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }
    }
}
