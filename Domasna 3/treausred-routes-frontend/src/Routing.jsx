import { useEffect } from "react";
import L from "leaflet";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";
import "leaflet-routing-machine";
import { useMap } from "react-leaflet";

L.Marker.prototype.options.icon = L.icon({
    iconUrl: "https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png"
});

export default function Routing() {
    const map = useMap();

    useEffect(() => {
        if (!map) return;

        const routingControl = L.Routing.control({
            waypoints: [L.latLng(41.0437013, 21.3317759), L.latLng(41.4142577, 21.4116669)],
            routeWhileDragging: true
        }).addTo(map);

        return () => map.removeControl(routingControl);
    }, [map]);

    return null;
}
