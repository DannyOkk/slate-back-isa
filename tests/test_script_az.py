import importlib.util
import unittest
from pathlib import Path


SCRIPT_PATH = Path(__file__).parents[1] / "script_az.py"
SPEC = importlib.util.spec_from_file_location("script_az", SCRIPT_PATH)
script_az = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(script_az)


class ConfigDefaultsTest(unittest.TestCase):
    def test_defaults_target_slate_resources_and_cost_safe_replicas(self):
        config = script_az.Config({})

        self.assertEqual(config.resource_group, "universidad")
        self.assertEqual(config.acr_name, "acringenieria")
        self.assertEqual(config.environment, "slate-isa")
        self.assertEqual(config.app_name, "slate-back-isa")
        self.assertEqual(config.image_name, "slate-backend")
        self.assertEqual(config.target_port, "8080")
        self.assertEqual(config.min_replicas, "0")
        self.assertEqual(config.max_replicas, "1")

    def test_cli_auth_is_allowed_when_service_principal_is_not_configured(self):
        config = script_az.Config({})

        self.assertEqual(config.validate_credentials(), [])

    def test_scope_rejects_resources_that_are_not_slate(self):
        config = script_az.Config({"CONTAINERAPP_NAME": "goat-grup-one"})

        self.assertIn("CONTAINERAPP_NAME", "\n".join(config.validate_slate_scope()))


if __name__ == "__main__":
    unittest.main()
